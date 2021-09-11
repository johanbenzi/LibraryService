import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return a 400 when creating book with invalid category"
    request {
        method PUT()
        url "/library/book"
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        body(
                title: anyNonBlankString(),
                author: anyNonBlankString(),
                categories: [99, anyPositiveInt()]
        )
    }
    response {
        status 400
    }
}