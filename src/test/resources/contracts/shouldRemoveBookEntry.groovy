import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should delete a book from library"
    request {
        method DELETE()
        url "/library/book/1"
    }
    response {
        status 204
    }
}