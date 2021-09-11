import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should loan upto 3 books if available"
    request {
        method POST()
        url "/library/user/1/books"
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        body([4])
    }
    response {
        status 406
        body("Book 4 is not available")
    }
}