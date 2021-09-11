import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should create a new category"
    request {
        method PUT()
        url "/library/category"
        body("Category A")
    }
    response {
        status 201
        body(anyPositiveInt())
    }
}