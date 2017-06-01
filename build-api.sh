echo Going to path...

cd /home/eduardo/projetos/devtools/apidevtools/server/

echo Clean

mvn clean

echo Package

mvn package -DskipTests

echo Moving...

mv /home/eduardo/projetos/devtools/apidevtools/server/target/apidevtools.war /home/eduardo/wildfly-10.1.0.Final/standalone/deployments/

echo Initializing server...

/home/eduardo/wildfly-10.1.0.Final/bin/standalone.sh

