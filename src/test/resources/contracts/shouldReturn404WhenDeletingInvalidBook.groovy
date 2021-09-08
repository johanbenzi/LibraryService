import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return 404 when deleting a book that doesn't exist"
    request {
        method DELETE()
        url "/book/2"
    }
    response {
        status 404
    }
}