<?php

namespace App\Rules;

use Closure;
use Illuminate\Contracts\Validation\ValidationRule;

class MaxPasswordBytes implements ValidationRule
{
    public function __construct(private readonly int $bytes = 72) {}

    public function validate(string $attribute, mixed $value, Closure $fail): void
    {
        if (is_string($value) && strlen($value) > $this->bytes) {
            $fail("The {$attribute} field must not exceed {$this->bytes} bytes.");
        }
    }
}
