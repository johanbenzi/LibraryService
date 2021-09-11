import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return 409 when creating a duplicate category"
    request {
        method PUT()
        url "/library/category"
        body("Category B")
    }
    response {
        status 409
        body("Category already exists")
    }
}