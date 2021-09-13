load("@ytt:assert", "assert")
load("@ytt:data", "data")
load("@ytt:struct", "struct")


def validate(values):
    values.scale or assert.fail("missing")
    values.app_name or assert.fail("missing")
    values.environment or assert.fail("missing")

    return values
end

values = validate(data.values)
