<?php

namespace App\Support;

use App\Exceptions\ApiException;
use Illuminate\Support\Facades\Crypt;
use JsonException;
use Throwable;

final class CursorToken
{
    /**
     * @param  array<string, mixed>  $context
     * @param  array<string, mixed>  $position
     */
    public function encode(array $context, array $position): string
    {
        try {
            return Crypt::encryptString(json_encode(
                ['v' => 1, 'context' => $context, 'position' => $position],
                JSON_THROW_ON_ERROR,
            ));
        } catch (JsonException $exception) {
            throw new ApiException('INTERNAL_SERVER_ERROR', 'Unable to create cursor.', 500);
        }
    }

    /**
     * @return array{context: array<string, mixed>, position: array<string, mixed>}
     */
    public function decode(string $cursor): array
    {
        try {
            $payload = json_decode(
                Crypt::decryptString($cursor),
                true,
                flags: JSON_THROW_ON_ERROR,
            );
        } catch (Throwable) {
            throw new ApiException(
                'VALIDATION_ERROR',
                'The given data was invalid.',
                422,
                ['cursor' => ['The cursor is invalid.']],
            );
        }

        if (
            ! is_array($payload)
            || ($payload['v'] ?? null) !== 1
            || ! is_array($payload['context'] ?? null)
            || ! is_array($payload['position'] ?? null)
        ) {
            throw new ApiException(
                'VALIDATION_ERROR',
                'The given data was invalid.',
                422,
                ['cursor' => ['The cursor is invalid.']],
            );
        }

        return [
            'context' => $payload['context'],
            'position' => $payload['position'],
        ];
    }

    public function mismatch(string $field = 'cursor'): never
    {
        throw new ApiException(
            'VALIDATION_ERROR',
            'The given data was invalid.',
            422,
            [$field => ['The cursor does not match the requested filters.']],
        );
    }

    public function inaccessible(): never
    {
        throw new ApiException('ITEM_NOT_FOUND', 'Item not found.', 404);
    }
}
