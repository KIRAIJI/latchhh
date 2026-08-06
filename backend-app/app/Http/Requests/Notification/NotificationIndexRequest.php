<?php

namespace App\Http\Requests\Notification;

use App\Enums\NotificationType;
use App\Http\Requests\ApiFormRequest;
use Illuminate\Validation\Rule;

class NotificationIndexRequest extends ApiFormRequest
{
    public function rules(): array
    {
        return [
            'type' => ['nullable', Rule::enum(NotificationType::class)],
            'read_state' => ['nullable', Rule::in(['all', 'unread', 'read'])],
            'cursor' => ['nullable', 'string', 'max:4096'],
            'per_page' => ['nullable', 'integer', 'between:1,100'],
        ];
    }
}
