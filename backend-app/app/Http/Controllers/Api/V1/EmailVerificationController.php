<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\Models\User;
use App\Support\ApiResponse;
use Illuminate\Auth\Events\Verified;
use Illuminate\Http\Request;

class EmailVerificationController extends Controller
{
    public function send(Request $request)
    {
        if (! $request->user()->hasVerifiedEmail()) {
            $request->user()->sendEmailVerificationNotification();
        }

        return ApiResponse::noContent();
    }

    public function verify(Request $request, int $id, string $hash)
    {
        $user = User::query()->find($id);
        $valid = $request->hasValidSignature()
            && $user
            && hash_equals(sha1($user->getEmailForVerification()), $hash);

        if (! $valid) {
            return $this->result('invalid');
        }

        if (! $user->hasVerifiedEmail() && $user->markEmailAsVerified()) {
            event(new Verified($user));
        }

        return $this->result('success');
    }

    private function result(string $status)
    {
        $verified = $status === 'success';

        return response()
            ->view('email-verification-result', [
                'verified' => $verified,
                'appLink' => config('latch.mobile.deep_link_scheme')
                    .'://email-verified?status='.$status,
            ], $verified ? 200 : 422)
            ->header('Cache-Control', 'no-store, private');
    }
}
