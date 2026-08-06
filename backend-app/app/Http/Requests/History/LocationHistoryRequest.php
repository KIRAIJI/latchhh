<?php

namespace App\Http\Requests\History;

use App\Http\Requests\ApiFormRequest;

class LocationHistoryRequest extends ApiFormRequest
{
    public function rules(): array
    {
        return [
            'from' => ['nullable', 'date_format:Y-m-d\TH:i:sP', 'required_with:to'],
            'to' => ['nullable', 'date_format:Y-m-d\TH:i:sP', 'required_with:from'],
            'cursor' => ['nullable', 'string', 'max:4096'],
            'per_page' => ['nullable', 'integer', 'between:1,1000'],
        ];
    }
}
