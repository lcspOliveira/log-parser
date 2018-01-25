### PARSER


  ## About :

	The goal is to write a parser in Java that parses web server access log file, loads the log to MySQL and checks for IP addresses that made more than a certain number of requests for the given duration. These IP addresses should be printed on console and also load them to another MySQL table with comments on why it's blocked.


  ## How it works :
    
    The tool takes "logfile", "startDate", "duration" and "threshold" as command line arguments. "logfile" is the path to the file containing the logs, "startDate" is of "yyyy-MM-dd.HH:mm:ss" format, "duration" can take only "hourly", "daily" as inputs and "threshold" can be an integer.
	An application.properties file should be provided on the same folder with the required parameters to access an MySQL database (url, username and password).
	
	Once the application is started it will automatically create the MySQL schemas and load all the access logs contained in the provided file to it. Then check for the IP addresses that exceded the request limit, print the addresses on the console and load them to the blocked addresses table.
    
    # Usage example : 
      
      java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
      
      application.properties sample:
		spring.datasource.url=jdbc:mysql://localhost:3306/web_log_parser?verifyServerCertificate=false&useSSL=true		
		spring.datasource.username=root
		spring.datasource.password=

    
  ## Log file format:
      
      Date("yyyy-MM-dd HH:mm:ss.SSS") | IP | Request | Status | User | Agent
      
    # Example:
		2017-01-01 23:57:23.715|192.168.92.194|"GET / HTTP/1.1"|200|"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"
		
  ## Deliverables
	
	#  (1) Java program that can be run from command line
		An executable jar is provided on the bin folder with a sample application.properties and logFile
	
	#  (2) Source Code for the Java program
		Maven project included
		
	# (3) MySQL schema used for the log data and (4) SQL queries for SQL test:
		queries and schemas included on 'SQLQueries' folder
