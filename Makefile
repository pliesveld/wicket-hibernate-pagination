SILENT=true

server:
	mvn exec:java -Dexec.mainClass=org.hsqldb.Server -Dexec.args="-silent ${SILENT} -database.0 mem:target/test -dbname.0 test"

server_file:
	mvn exec:java -Dexec.mainClass=org.hsqldb.Server -Dexec.args="-silent ${SILENT} -database.0 file:target/test -dbname.0 test"

manager:
	mvn exec:java -Dexec.mainClass=org.hsqldb.util.DatabaseManagerSwing
