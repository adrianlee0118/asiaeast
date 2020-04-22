# asiaeast
Creates short trip itineraries for would-be travelers by grouping destination attractions based on trip length in days (up to 7 currently).

NOTE: Certain elements of this project are currently under revision.

App takes destination (East Asian city) and trip length from the user and groups attractions at that location by proximity so that on each
day of the trip, travelers will have a list of places to visit that are relatively close to each other. Attractions for all cities that the app supports are stored in a cloud database (Google Firebase), although a future consideration will be to plug directly into a travel
website database via an API to allow the app to support many locations.

asiaeast is connected to Google Maps API and outputs the groupings visually on the map interface. It also outputs a schedule-format itinerary that the user can send to their email.

Future considerations will be to add additional filters to the input process (i.e. near coffee shops, physical strain required, near public transportation, safe/unsafe etc.)
