import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return a 409 when attempting to create a book which already exists"
    request {
        method PUT()
        url "/library/book"
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        body(
                title: "The other awesome book",
                author: "John Doe",
                categories: [anyPositiveInt()]
        )
    }
    response {
        status 409
        body("Book already exists")
    }
}