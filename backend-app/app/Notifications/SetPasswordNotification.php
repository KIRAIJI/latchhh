<?php

namespace App\Notifications;

use Illuminate\Notifications\Messages\MailMessage;

class SetPasswordNotification extends ResetPasswordNotification
{
    public function toMail($notifiable): MailMessage
    {
        $url = route('password.bridge', [
            'token' => $this->token,
            'email' => $notifiable->getEmailForPasswordReset(),
            'mode' => 'setup',
        ]);

        return (new MailMessage)
            ->subject('Set Your LATCH Password')
            ->line('You requested a password for your LATCH account.')
            ->action('Set Password', $url)
            ->line(
                'This password setup link will expire in '.
                config('auth.passwords.'.config('auth.defaults.passwords').'.expire').
                ' minutes.',
            )
            ->line('If you did not request this, no further action is required.');
    }
}
