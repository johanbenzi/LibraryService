import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return 404 when attempting to loan books which are not available"
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
        status 404
        body("Book 4 is not available")
    }
}