import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return books by user"
    request {
        method PATCH()
        url "/library/user/1/books"
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        body([1, 2, 3])
    }
    response {
        status 204
    }
}