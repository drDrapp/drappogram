create table dg_user_subscriptions
(
    channel_id    bigint not null references dg_user,
    subscriber_id bigint not null references dg_user,
    primary key (channel_id, subscriber_id)
)