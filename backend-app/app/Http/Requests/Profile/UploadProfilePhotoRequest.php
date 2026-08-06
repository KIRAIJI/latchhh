<?php

namespace App\Http\Requests\Profile;

use App\Http\Requests\ApiFormRequest;

class UploadProfilePhotoRequest extends ApiFormRequest
{
    public function rules(): array
    {
        return [
            'photo' => ['required', 'file', 'image', 'mimes:jpeg,jpg,png,webp', 'max:5120'],
        ];
    }
}
