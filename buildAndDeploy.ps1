$workDir = Get-Location
$containers = docker ps --format "{{.Names}}"
$requiredContainers = @("ssbd02-tomcat", "ssbd02-db")

$containersRunning = $true

foreach ($containerName in $requiredContainers) {
    if ($containers -notcontains $containerName) {
        $containersRunning = $false
        break
    }
}

if (-not $containersRunning) {
    Write-Host "Error: Required containers are not running. Starting the containers..."

    docker-compose -f docker-ssbd02/prod.docker-compose.yml up -d

    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error: Failed to start the containers. Exiting..."
        exit 1
    }

    Write-Host "Containers started successfully."
}


Write-Host "Building the project..."
mvn clean package

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed. Exiting..."
    exit 1
}

Write-Host "Build successful. Deploying the project..."
docker cp "$workDir/target/ssbd02.war" ssbd02-tomcat:/usr/local/tomcat/webapps/ssbd02.war

Write-Host "Deployed successfully. Restarting the Tomcat server..."
docker restart ssbd02-tomcat

Write-Host "Deployment successful."
exit 0