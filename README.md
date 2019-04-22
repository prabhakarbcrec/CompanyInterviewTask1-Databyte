# CompanyInterviewTask1-DataInovation

Task 1:

The json transformer service is a webservice which accepts a JSON payload (via an HTTP PUT or HTTP POST action) and performs an action on it based on the endpoint the payload is received on. The JSON transformer service has three endpoints

Endpoint	Function	HTTP Verb
/alpha	Alphabetize the keys in the request JSON payload and return the resulting JSON back in the HTTP response	PUT
/flatten	Flatten any JSON Arrays in the request JSON payload (comma separated) such that the resulting JSON does not contain any JSON Arrays	POST
/status	Obtains the health status of the system and responds with the details in the HTTP response	GET
examples
/alpha
input HTTP PUT /alpha

{
  "fruit":"apple",
  "animal":"zebra",
  "city-list":["sunnyvale","sanjose"]
}
output

{
  "animal":"zebra",
  "city-list":["sunnyvale","sanjose"],
  "fruit":"apple"
}
/flatten
input HTTP PUT /flatten

{
  "fruit":"apple",
  "animal":"zebra",
  "city-list":["sunnyvale","sanjose"]
}
output

{
  "fruit":"apple",
  "animal":"zebra",
  "city-list":"sunnyvale,sanjose"
}
/status
input HTTP GET /status

output

{
  "mem-used-pct" : 83.6,
  "disc-space-avail" : [
                         { "discname" : "/dev/SDA1", "availbytes" : "12345000"},
                         { "discname" : "/dev/SDA2", "availbytes" : "12345000"}
                       ],
  "cpu-used-pct" : 55.0
}
