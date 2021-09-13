import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should create a new book entry"
    request {
        method PUT()
        url "/library/book"
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        body(
                title: "Awesome book",
                author: $(producer("John Doe"), consumer(anyNonBlankString())),
                categories: [1]
        )
    }
    response {
        status 201
        body(anyPositiveInt())
    }
}