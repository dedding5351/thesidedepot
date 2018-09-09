
import pymongo
import ssl
from pymongo import MongoClient



client = MongoClient('mongodb+srv://admin:siderift@cluster0-1jnpy.mongodb.net/test?retryWrites=true', ssl=True, ssl_cert_reqs=ssl.CERT_NONE)

# Get the sampleDB database
db = client.database
collection = db.usersNewA
username = "admin"
password = "pass"

query = {'username' : 'admin'}

user = {
	'username' : username,
	'password' : password,
	'projects' : None,
	'badges' : None,
	'firstLogin' : 1


}

#collection.insert_one(user)
count = 0
for item in collection.find():
	count = count + 1
	print(count)
	

