import pandas
from selenium import webdriver
import pymongo
import ssl
from pymongo import MongoClient



client = MongoClient('mongodb+srv://admin:siderift@cluster0-1jnpy.mongodb.net/test?retryWrites=true', ssl=True, ssl_cert_reqs=ssl.CERT_NONE)
db = client.database
collection = db.projectsFinalA


path_to_chromedriver = './scraping/chromedriver'
browser = webdriver.Chrome(executable_path = path_to_chromedriver)


datafile = pandas.read_csv('SideDepotBetter - Sheet1.csv')


numRows = datafile.shape[0]


for x in range(0,numRows):
	currentFrame = datafile.loc[x]
	difficulty = currentFrame["Difficulty"]
	category = currentFrame["Category"]
	time = currentFrame["Time"]
	description = currentFrame["Description"]
	url = currentFrame["Website"]
	browser.get(url)

	pageTitle = browser.find_elements_by_xpath('//*[@id="container"]/div[2]/div[1]/div/h1')[0].text
	pageDescription = description;
	pageHeaders = browser.find_elements_by_xpath('//div[@class="step-title"]')
	parsedHeaders = []
	for item in pageHeaders:
		parsedHeaders.append(item.text)
	steps = browser.find_elements_by_xpath('//div[@class="content"]/div/p')
	parsedSteps = []
	for item in steps:
		parsedSteps.append(item.text)

	#Print Individual/Will be stored in DB soon!
	#print(pageHeaders[0].text)
	#print(parsedSteps[0])

	toolsAndMaterials = browser.find_elements_by_xpath('//div[@class="content"]/ul/li/a')
	webCollect = []
	
	for item in toolsAndMaterials:
		webCollect.append(item.get_attribute('href'))


	listItems = []	

	for item in toolsAndMaterials:
		listItems.append(item.text)

	priceEstimate = 0.0;
	for item in webCollect:
		browser.get(item)

		try:
			priceMajor = browser.find_elements_by_xpath('//*[@id="products"]/div/div[1]/div/div[3]/div[4]/div')[0].text

			priceMinor = browser.find_elements_by_xpath('//*[@id="products"]/div/div[1]/div/div[3]/div[4]/div/span[2]')[0].text[1:2]
			priceEstimate += float(priceMajor[1:len(priceMajor)-2]) + (float(priceMinor)/100)
			#print(priceMinor)
			#print(priceMajor[1:len(priceMajor)-2])
		except Exception, error:
			pass #Nothing to see here bois. Nothing to see.....
			
		

	
	#print(priceEstimate)
	#Consider that I can trim out the bottom 3 here...

	image = browser.find_elements_by_xpath('//div[@class="content_image"]/img')
	try:
		image = image[0].get_attribute('src') # This is the image link
	except Exception, error:
		image = "Empty"
	#print(pageTitle)
	#print(image)
	dbObject = {
		'title' : pageTitle,
		'description' : pageDescription,
		'difficulty' : difficulty,
		'category' : category,
		'toolsAndMaterials' : listItems,
		'time' : time,
		'image' : image,
		'priceEstimate' : priceEstimate,
		'parsedSteps' : parsedSteps,
		'parsedHeaders' : parsedHeaders,
		'weblinks' : webCollect
	}

	print(webCollect)
	#postid = collection.insert_one(dbObject)
	


	#print("Testing: " + category + " " + description + " " + website + " " + time + " " + difficulty)




