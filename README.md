# Walmart FetchCountries Codelab
Fetch Countries coding exercise

#Problem Statement: 

Fetch data from a URL and then display it using the following criteria: 
- Items are displayed using recycler view instead of compose
- DiffUtil is used
- Error scenarios are handled

# Architecture and design: 

I followed MVVM pattern here to separate all the layers (Models with the View and the View with the ViewModel). Due to the time restraint of this excercise a sequence diagram was not created but essentially the flow would be as follows: 
User -> Activity (View) -> Request Data (ViewModel, viewModel) -> Retrieve Data (ViewModel, Service) -> Parse Data (ViewModel, Model) -> Update View

Things to improve (Future):

- Ability to sort data using country names or other business criteria
- Another thing that is missing from this solution is the ability to offline cache and show the results. This would be done using a DB.
- I know right now the service is open source API but in case if it becomes authenticated we will need a login mechanism into the app.
- Better Error messaging. Something to add would be alert dialogs to show in case something went wrong with the option to re-fetch the data
- More testing in this project can benefit it significantly.
- Handle the network errors better. I created the result wrapper but did not get a chance to hook it up. I also do not know the error message structure coming back from the server so doing this would have been futile.
``
# Threat Assessment: 

This solution assumes the backend is safe from man in the middle attacks since the only API exposed is a public GET API. 
Since the data is dummy data and not customer PII there is no Data in transition or Data at Rest issues present.
However since the URL of the GET request is exposed it can potentially lead to DDOS attacks by a malicious attacker. Solution would be to have this api under an authenticated gateway OR fast fail traffic originating from a single IP address.

# Time: 

This assessment took 2.5 - 3 hours to complete
