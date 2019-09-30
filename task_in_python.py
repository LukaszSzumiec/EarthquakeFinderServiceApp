import requests
import math

class Earthquake:
	def __init__(self, *arg, **kwargs):
		super(Earthquake, self).__init__()
		self.arg = arg
		self.magnitudo = arg[0]
		self.description = arg[1]
		self.x_coord = arg[2]
		self.y_coord = arg[3]
		self.distance = None


url = "https://earthquake.usgs.gov/fdsnws/event/1/query"

def type_input():
	global x_input
	global y_input
	print("Example of correct data: \n\tType latitude coord:	30.15\n\t\t",
			"Type longitude coord:	25\n\t\t\t",
			"latitude range -90 to 90\n\t\t\t\tlongitude range -180 to 180")

	x_input = input("Type latitude coord:	")	#dopisac instrukc12je jak ma wygladac input
	y_input = input("Type longitude coord:	")

	try:
		x_input = float(x_input)	#Check if input is digit
		y_input = float(y_input)

		if x_input >180.0 or x_input < -180.0 or y_input>90.0 or y_input<-90.0:
			raise Exception()

	except:
		print("\n\nInvalid input. Example of correct data: \n\tType latitude coord:	30.15\n\t\t",
			"Type longitude coord:	25\n\t\t\t"
			"latitude range -90 to 90\n\t\t\t\tlongitude range -180 to 180")
		type_input()

type_input()

PARAMS = {
	'format':'geojson',
	'latitude': x_input,
	'longitude': y_input,
	'maxradius': 12}


def check(resp):
	r = requests.get(url=url, params=PARAMS)
	# print(r)
	resp = dict(r.json())
	if int(resp['metadata']['count']) < 20:
		# print("leci")
		PARAMS['maxradius'] += 5
		resp = check(resp)
		return resp
	else:
		return resp

a = check(None)

earthquake_list = []

def check_if_contains(new_earthquake):
	if len(earthquake_list) != 0:
		for earthquake in earthquake_list:
			if earthquake.x_coord ==  new_earthquake.x_coord or earthquake.y_coord == new_earthquake.y_coord:
				return True
		return False
	else:
		return False

def calculate_distance(earthquake):
	degrees_to_radians = math.pi/180.0
	phi1 = (90.0 - x_input)*degrees_to_radians
	phi2 = (90.0 - earthquake.x_coord)*degrees_to_radians

	theta1 = y_input*degrees_to_radians
	theta2 = earthquake.y_coord*degrees_to_radians

	cos = (math.sin(phi1)*math.sin(phi2)*math.cos(theta1 - theta2) +
	math.cos(phi1)*math.cos(phi2))
	arc = math.acos( cos )
	earthquake.distance = round(arc * 6373)

iterator = 0
for feature in a['features']:
	new_earthquake = Earthquake(
		round(float(feature['properties']['mag']), 2),
		feature['properties']['place'],
		feature['geometry']['coordinates'][1],
		feature['geometry']['coordinates'][0])
	if check_if_contains(new_earthquake) is False:
		calculate_distance(new_earthquake)
		earthquake_list.append(new_earthquake)
		cursor = earthquake_list[iterator]
		pos = iterator
		while pos > 0 and earthquake_list[pos - 1].distance > cursor.distance:
			earthquake_list[pos] = earthquake_list[pos - 1]
			pos = pos - 1
		iterator += 1
		earthquake_list[pos] = cursor

earthquake_list = earthquake_list[:10]

for earthquake in earthquake_list:
	print(
		"M: " , earthquake.magnitudo ,
		" - " , earthquake.description,
		"||", earthquake.distance, " km")
