# Windows + PostgreSQL 17 TimeZone Issue

## Problem

Some Windows + JDK combinations expose the JVM default timezone as:

Asia/Calcutta

PostgreSQL 17 rejects this identifier.

## Temporary Development Fix

System.setProperty("user.timezone", "Asia/Kolkata");

inside NexusApplication.

This line will be removed before deployment.

Production containers run with UTC timezone.