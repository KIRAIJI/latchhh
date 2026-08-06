<?php

namespace App\Support;

use Illuminate\Http\JsonResponse;
use Symfony\Component\HttpFoundation\Response;

final class ApiResponse
{
    /**
     * @param  array<string, mixed>|list<mixed>|object|null  $data
     * @param  array<string, mixed>|null  $meta
     */
    public static function success(
        mixed $data,
        string $message,
        int $status = Response::HTTP_OK,
        ?array $meta = null,
    ): JsonResponse {
        $payload = [
            'success' => true,
            'message' => $message,
            'data' => $data,
        ];

        if ($meta !== null) {
            $payload['meta'] = $meta;
        }

        return response()->json($payload, $status);
    }

    public static function error(
        string $code,
        string $message,
        int $status,
        array $errors = [],
        array $additional = [],
    ): JsonResponse {
        $payload = [
            'success' => false,
            'message' => $message,
            'code' => $code,
        ];

        if ($errors !== []) {
            $payload['errors'] = $errors;
        }

        $payload += $additional;

        return response()->json($payload, $status);
    }

    public static function noContent(): Response
    {
        return response()->noContent();
    }
}
