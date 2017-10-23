# MobileProgramming

I need an application witch will help me to manage the films from the cinemas from cluj napoca. I want people to be see
all the cinemas in one place, and also to search for different films and see specifics about each of them.
All the people witch will use the application will need to have an account and based on their account, i want them
to be able to rate the films. Knowing somehow the popularity of each cinema would also be very nice!

The application will manage the cinemas from Cluj Napoca and will have the following functionalities:
	-> search films
	-> display a chart showing the number of sold tickets for each cinemas in the last week
	-> show list with all the cinemas
	-> show film details
	-> create user account
	-> rate the films(supplying the nb_of_stars and a comment)

ativities for:
	a. log-in form, authentification button
	b. authentification form, back button
	c. search box (for movies), chart, list of cinemas, show_cinemas_on_map button
	d. film details(also the cinemas where is playied in the next week, it's raitings), 
	   rate_film button(only if the film wasn't rated before by the logged-in user), back button
	e. rate film form, back button

a(log-in success) => c
a(authentification_button clicked) => b
b(authentification succesfull) => c
b(back_button clicked) => a
c(select film) => d
c(show_cinemas_on_map clicked) => go to google maps
d(rate_button clicked)  => e
d(back_button clicked) => c
e(film rated || back button clicked) => d