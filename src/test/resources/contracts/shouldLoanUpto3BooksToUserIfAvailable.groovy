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
        body([1, 2, 3])
    }
    response {
        status 200
        body([
                [
                        id        : 1,
                        title     : anyNonBlankString(),
                        author    : anyNonBlankString(),
                        categories: [anyNonBlankString()]
                ],
                [
                        id        : 2,
                        title     : anyNonBlankString(),
                        author    : anyNonBlankString(),
                        categories: [anyNonBlankString()]
                ],
                [
                        id        : 3,
                        title     : anyNonBlankString(),
                        author    : anyNonBlankString(),
                        categories: [anyNonBlankString()]
                ]
        ])
    }
}