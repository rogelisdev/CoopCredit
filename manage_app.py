import subprocess
import time
import sys
import argparse
import urllib.request
import urllib.error

# Configuration
DOCKER_COMPOSE_CMD = ["sudo", "docker-compose"] # Wraps the standard docker-compose command
SERVICE_URL = "http://localhost:8085/actuator/health"
SERVICE_NAME = "credit-application-service"

def run_command(command, stream_output=False, interactive=False):
    """Runs a shell command."""
    try:
        if interactive:
            # Run interactively, letting stdout/stderr flow to the terminal directly
            # This is crucial for 'sudo' to work if it asks for a password.
            result = subprocess.run(command)
            return result.returncode == 0
        elif stream_output:
            # Popen allows us to stream output in real-time
            process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
            for line in process.stdout:
                print(line, end="")
            process.wait()
            return process.returncode
        else:
            result = subprocess.run(command, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
            return result.stdout.strip()
    except subprocess.CalledProcessError as e:
        print(f"Error executing command: {' '.join(command)}")
        try:
            print(e.stderr)
        except:
             pass
        return None
    except KeyboardInterrupt:
         print("\nOperation cancelled.")
         return None
    except FileNotFoundError:
        print(f"Error: Command '{command[0]}' not found. Please ensure it is installed and in your PATH.")
        return None

def configure_docker_command():
    """Checks Docker connectivity and configures the command to use."""
    global DOCKER_COMPOSE_CMD
    print("🔍 Configuring Docker environment...")

    # Option 1: Try 'docker compose' (plugin) without sudo (Best for Docker Desktop)
    print("   Testing 'docker compose' (no sudo)...", end=" ")
    try:
        subprocess.run(["docker", "compose", "version"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        # Check if we can actually talk to daemon
        subprocess.run(["docker", "info"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        print("✅ Success!")
        DOCKER_COMPOSE_CMD = ["docker", "compose"]
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("❌")

    # Option 2: Try 'docker-compose' (standalone) without sudo
    print("   Testing 'docker-compose' (no sudo)...", end=" ")
    try:
        subprocess.run(["docker-compose", "version"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        subprocess.run(["docker", "info"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        print("✅ Success!")
        DOCKER_COMPOSE_CMD = ["docker-compose"]
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("❌")

    # Option 3: Try 'docker compose' WITH sudo
    print("   Testing 'sudo docker compose'...", end=" ")
    try:
        subprocess.run(["sudo", "docker", "compose", "version"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        subprocess.run(["sudo", "docker", "info"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        print("✅ Success!")
        DOCKER_COMPOSE_CMD = ["sudo", "docker", "compose"]
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("❌")

    # Option 4: Try 'docker-compose' WITH sudo
    print("   Testing 'sudo docker-compose'...", end=" ")
    try:
        subprocess.run(["sudo", "docker-compose", "version"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        subprocess.run(["sudo", "docker", "info"], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=True)
        print("✅ Success!")
        DOCKER_COMPOSE_CMD = ["sudo", "docker-compose"]
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("❌")

    print("\n⚠️  Could not find a working Docker configuration.")
    print("1. Ensure Docker is running.")
    print("2. Ensure you have 'docker-compose' or 'docker compose' plugin installed.")
    
    # Fallback/Force option
    print("\nForce mode: Which command do you want to use?")
    print("1. docker compose")
    print("2. docker-compose")
    print("3. sudo docker compose")
    print("4. sudo docker-compose")
    choice = input("Select (1-4) or press Enter to abort: ")
    
    if choice == '1': DOCKER_COMPOSE_CMD = ["docker", "compose"]
    elif choice == '2': DOCKER_COMPOSE_CMD = ["docker-compose"]
    elif choice == '3': DOCKER_COMPOSE_CMD = ["sudo", "docker", "compose"]
    elif choice == '4': DOCKER_COMPOSE_CMD = ["sudo", "docker-compose"]
    else: return False
    
    return True

def start_services():
    """Starts services using docker-compose."""
    if not configure_docker_command():
        return False

    print("🚀 Starting services...")
    # Use interactive=True so sudo prompt is visible if needed
    success = run_command(DOCKER_COMPOSE_CMD + ["up", "-d", "--build"], interactive=True)
    if success:
        print("✅ Services started (detached mode).")
        return True
    else:
        print("❌ Failed to start services. Please checks the logs above.")
        return False

def stop_services():
    """Stops services."""
    if not configure_docker_command():
       return
       
    print("🛑 Stopping services...")
    success = run_command(DOCKER_COMPOSE_CMD + ["down"], interactive=True)
    if success:
         print("✅ Services stopped.")
    else:
         print("❌ Failed to stop services.")

def view_logs(follow=True):
    """Views logs for the main service."""
    if not configure_docker_command():
        return

    print(f"📋 Fetching logs for {SERVICE_NAME} (Ctrl+C to exit)...")
    cmd = DOCKER_COMPOSE_CMD + ["logs"]
    if follow:
        cmd.append("-f")
    cmd.append(SERVICE_NAME)
    
    try:
        run_command(cmd, interactive=True)
    except KeyboardInterrupt:
        print("\nStopped viewing logs.")

def check_health(timeout=60):
    """Polls the service health endpoint."""
    print(f"🏥 Checking health at {SERVICE_URL} (Timeout: {timeout}s)...")
    start_time = time.time()
    
    while time.time() - start_time < timeout:
        try:
            with urllib.request.urlopen(SERVICE_URL) as response:
                if response.status == 200:
                    print("\n✅ Service is HEALTHY and responding!")
                    return True
        except (urllib.error.URLError, ConnectionResetError):
            pass
            
        sys.stdout.write(".")
        sys.stdout.flush()
        time.sleep(2)
        
    print("\n❌ Health check timed out. Service might not be ready or failed to start.")
    print("Tip: Check the logs using 'python3 manage_app.py logs'")
    return False

def main():
    parser = argparse.ArgumentParser(description="Manage the Credit Application Service Docker environment.")
    subparsers = parser.add_subparsers(dest="action", help="Action to perform")
    
    # CLI Commands
    subparsers.add_parser("start", help="Start services and wait for health")
    subparsers.add_parser("stop", help="Stop services")
    subparsers.add_parser("restart", help="Restart services")
    subparsers.add_parser("logs", help="View logs (Ctrl+C to exit)")
    subparsers.add_parser("status", help="Check service status")

    args = parser.parse_args()

    # Pre-check Docker only for relevant commands if running interactive without args
    # But if args are provided, check inside the specific function calls or here.
    
    if args.action == "start":
        if start_services():
            if check_health():
                 print(f"\nApp is ready at http://localhost:8085")
    elif args.action == "stop":
        stop_services()
    elif args.action == "restart":
        stop_services()
        if start_services():
            check_health()
    elif args.action == "logs":
        view_logs()
    elif args.action == "status":
         check_health(timeout=5)
    else:
        # Interactive mode
        while True:
            print("\n--- 🐍 CoopCredit Operations Manager ---")
            print("1. 🚀 Start Services")
            print("2. 🛑 Stop Services")
            print("3. 🔄 Restart Services")
            print("4. 📋 View Logs")
            print("5. 🏥 Check Health")
            print("0. 👋 Exit")
            
            try:
                choice = input("\nSelect an option: ")
            except EOFError:
                break
                
            if choice == "1":
                if start_services():
                    check_health()
            elif choice == "2":
                stop_services()
            elif choice == "3":
                stop_services()
                if start_services():
                    check_health()
            elif choice == "4":
                view_logs()
            elif choice == "5":
                 check_health(timeout=5)
            elif choice == "0":
                print("Goodbye!")
                break
            else:
                print("Invalid option. Please try again.")

if __name__ == "__main__":
    main()
