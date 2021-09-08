import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should create a new book entry"
    request {
        method PUT()
        url "/book"
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        body(
                title: "Awesome book",
                author: anyNonBlankString(),
                categories: [anyPositiveInt()]
        )
    }
    response {
        status 201
        body(anyPositiveInt())
    }
}