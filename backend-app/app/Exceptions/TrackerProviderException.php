<?php

namespace App\Exceptions;

use RuntimeException;
use Throwable;

class TrackerProviderException extends RuntimeException
{
    public function __construct(
        public readonly string $category,
        string $message = 'Tracker provider request failed.',
        ?Throwable $previous = null,
    ) {
        parent::__construct($message, 0, $previous);
    }
}
