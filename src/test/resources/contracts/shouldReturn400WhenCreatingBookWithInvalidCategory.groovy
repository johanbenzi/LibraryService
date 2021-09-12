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
                title: "Great Book",
                author: "Jane Doe",
                categories: [99]
        )
    }
    response {
        status 400
        body("Category doesn't exist")
    }
}