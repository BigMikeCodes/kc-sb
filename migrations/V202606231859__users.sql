CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public."user" (
    id UUID NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL,
    validity tstzrange NOT NULL DEFAULT tstzrange(now(), 'infinity'::timestamptz),
    PRIMARY KEY (id, validity)
);

-- Ensure only ONE active version exists per user ID
CREATE UNIQUE INDEX IF NOT EXISTS user_id_active_idx ON public."user" (id)
WHERE (upper(validity) = 'infinity'::timestamptz);

-- Ensure Email is unique across all ACTIVE users
CREATE UNIQUE INDEX IF NOT EXISTS user_email_active_idx ON public."user" (email)
WHERE (upper(validity) = 'infinity'::timestamptz);

-- External mappings to keycloak
CREATE TABLE IF NOT EXISTS public."user_iam_mapping" (
    user_id UUID NOT NULL,
    iam_id TEXT NOT NULL,
    provider TEXT NOT NULL,
    CONSTRAINT unique_user_iam_mapping PRIMARY KEY (user_id, provider)
);

