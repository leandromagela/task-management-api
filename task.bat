@echo off
REM Print a message indicating the start of the process
echo Starting the build and deployment process...

REM Compile the application and skip tests
echo Running 'mvn clean package -DskipTests'...
call mvn clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 (
    echo Maven build failed. Exiting.
    exit /b %ERRORLEVEL%
)

REM Build the Docker images
call echo Running 'docker-compose build'...
docker-compose build
IF %ERRORLEVEL% NEQ 0 (
    echo Docker build failed. Exiting.
    exit /b %ERRORLEVEL%
)

REM Start the containers
call echo Running 'docker-compose up -d'...
docker-compose up -d
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to start Docker containers. Exiting.
    exit /b %ERRORLEVEL%
)

REM Print a message indicating the end of the process
echo Build and deployment process completed successfully.