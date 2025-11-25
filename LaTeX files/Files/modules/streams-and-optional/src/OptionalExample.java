private Optional<Response> getResponse(){
    //response might be null here
    Response response = ...;
    //this returns the response wrapped in an Optional or an empty Optional
    return new Optional.ofNullable(response);
}
//get the response or an empty Optional
Optional<Response> response = getResponse();
//run lambda function if response is present
response.ifPresent(res -> {
    switch(res.getStatusCode()){
        //Do anything here
    }
});