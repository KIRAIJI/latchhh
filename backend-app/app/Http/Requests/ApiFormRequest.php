<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Validator;

abstract class ApiFormRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function withValidator(Validator $validator): void
    {
        $rules = $this->rules();
        $allowed = array_keys($rules);

        foreach ($rules as $field => $fieldRules) {
            if (is_array($fieldRules) && in_array('confirmed', $fieldRules, true)) {
                $allowed[] = $field.'_confirmation';
            }
        }

        $validator->after(function (Validator $validator) use ($allowed): void {
            foreach (array_keys($this->all()) as $field) {
                if (! in_array($field, $allowed, true)) {
                    $validator->errors()->add(
                        $field,
                        "The {$field} field is prohibited.",
                    );
                }
            }
        });
    }
}
