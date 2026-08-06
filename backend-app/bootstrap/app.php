<?php

use App\Exceptions\ApiException;
use App\Http\Middleware\EnsureEmailIsVerified;
use App\Support\ApiResponse;
use Illuminate\Auth\Access\AuthorizationException;
use Illuminate\Auth\AuthenticationException;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Foundation\Application;
use Illuminate\Foundation\Configuration\Exceptions;
use Illuminate\Foundation\Configuration\Middleware;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Symfony\Component\HttpKernel\Exception\TooManyRequestsHttpException;

return Application::configure(basePath: dirname(__DIR__))
    ->withRouting(
        web: __DIR__.'/../routes/web.php',
        api: __DIR__.'/../routes/api.php',
        commands: __DIR__.'/../routes/console.php',
        health: '/up',
    )
    ->withMiddleware(function (Middleware $middleware): void {
        $middleware->redirectGuestsTo(fn (): null => null);
        $middleware->alias([
            'verified.api' => EnsureEmailIsVerified::class,
        ]);
    })
    ->withExceptions(function (Exceptions $exceptions): void {
        $exceptions->shouldRenderJsonWhen(
            fn (Request $request): bool => $request->is('api/*')
        );

        $exceptions->render(function (ApiException $exception, Request $request) {
            if (! $request->is('api/*')) {
                return null;
            }

            return ApiResponse::error(
                $exception->errorCode,
                $exception->getMessage(),
                $exception->status,
                $exception->errors,
            );
        });

        $exceptions->render(function (ValidationException $exception, Request $request) {
            if (! $request->is('api/*')) {
                return null;
            }

            return ApiResponse::error(
                'VALIDATION_ERROR',
                'The given data was invalid.',
                422,
                $exception->errors(),
            );
        });

        $exceptions->render(function (AuthenticationException $exception, Request $request) {
            if (! $request->is('api/*')) {
                return null;
            }

            return ApiResponse::error(
                'AUTH_UNAUTHENTICATED',
                'Unauthenticated.',
                401,
            );
        });

        $exceptions->render(function (TooManyRequestsHttpException $exception, Request $request) {
            if (! $request->is('api/*')) {
                return null;
            }

            $rawRetryAfter = $exception->getHeaders()['Retry-After'] ?? 60;
            $retryAfter = is_numeric($rawRetryAfter)
                ? max(1, (int) $rawRetryAfter)
                : 60;
            $isLogin = $request->is('api/v1/auth/login');
            $response = ApiResponse::error(
                'RATE_LIMITED',
                $isLogin
                    ? 'Too many sign-in attempts. Try again shortly.'
                    : 'Too many requests. Try again shortly.',
                429,
                additional: ['retry_after_seconds' => $retryAfter],
            );
            $response->headers->set('Retry-After', (string) $retryAfter);

            return $response;
        });

        $exceptions->render(function (
            AuthorizationException|ModelNotFoundException|NotFoundHttpException $exception,
            Request $request,
        ) {
            if (! $request->is('api/*')) {
                return null;
            }

            return ApiResponse::error(
                'RESOURCE_NOT_FOUND',
                'The requested resource was not found.',
                404,
            );
        });

        $exceptions->render(function (Throwable $exception, Request $request) {
            if (! $request->is('api/*')) {
                return null;
            }

            report($exception);

            return ApiResponse::error(
                'INTERNAL_SERVER_ERROR',
                'An unexpected error occurred.',
                500,
            );
        });
    })->create();
