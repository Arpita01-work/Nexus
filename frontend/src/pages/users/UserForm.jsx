import { useEffect } from "react";

import { useForm } from "react-hook-form";

import {
    Button,
    Grid,
    TextField,
    MenuItem,
} from "@mui/material";

export default function UserForm({
    user,
    organizations,
    onSubmit,
}) {

    const {
        register,
        handleSubmit,
        reset,
        formState: {
            errors,
        },
    } = useForm();

    useEffect(() => {

        if (user) {

            reset({
                organizationId:
                    user.organizationId || "",

                firstName:
                    user.firstName || "",

                lastName:
                    user.lastName || "",

                email:
                    user.email || "",

                phone:
                    user.phone || "",

                jobTitle:
                    user.jobTitle || "",

                active:
                    user.active ?? true,
            });

        } else {

            reset({
                organizationId: "",
                firstName: "",
                lastName: "",
                email: "",
                phone: "",
                jobTitle: "",
            });

        }

    }, [user, reset]);

    return (

        <form
            onSubmit={
                handleSubmit(onSubmit)
            }
        >

            <Grid
                container
                spacing={2}
                sx={{ mt: 1 }}
            >

                {/* Organization */}

                <Grid
                    size={{
                        xs: 12,
                    }}
                >

                    <TextField
                        select
                        label="Organization"
                        fullWidth
                        defaultValue=""
                        {...register(
                            "organizationId",
                            {
                                required:
                                    "Organization is required",
                            }
                        )}
                        error={
                            !!errors.organizationId
                        }
                        helperText={
                            errors
                                .organizationId
                                ?.message
                        }
                    >

                        <MenuItem value="">
                            Select Organization
                        </MenuItem>

                        {organizations.map(
                            (organization) => (

                                <MenuItem
                                    key={
                                        organization.id
                                    }
                                    value={
                                        organization.id
                                    }
                                >
                                    {
                                        organization.name
                                    }
                                </MenuItem>

                            )
                        )}

                    </TextField>

                </Grid>

                {/* First Name */}

                <Grid
                    size={{
                        xs: 12,
                        md: 6,
                    }}
                >

                    <TextField
                        label="First Name"
                        fullWidth
                        {...register(
                            "firstName",
                            {
                                required:
                                    "First name is required",
                            }
                        )}
                        error={
                            !!errors.firstName
                        }
                        helperText={
                            errors
                                .firstName
                                ?.message
                        }
                    />

                </Grid>

                {/* Last Name */}

                <Grid
                    size={{
                        xs: 12,
                        md: 6,
                    }}
                >

                    <TextField
                        label="Last Name"
                        fullWidth
                        {...register(
                            "lastName",
                            {
                                required:
                                    "Last name is required",
                            }
                        )}
                        error={
                            !!errors.lastName
                        }
                        helperText={
                            errors
                                .lastName
                                ?.message
                        }
                    />

                </Grid>

                {/* Email */}

                <Grid
                    size={{
                        xs: 12,
                        md: 6,
                    }}
                >

                    <TextField
                        label="Email"
                        type="email"
                        fullWidth
                        {...register(
                            "email",
                            {
                                required:
                                    "Email is required",
                            }
                        )}
                        error={
                            !!errors.email
                        }
                        helperText={
                            errors
                                .email
                                ?.message
                        }
                    />

                </Grid>

                {/* Phone */}

                <Grid
                    size={{
                        xs: 12,
                        md: 6,
                    }}
                >

                    <TextField
                        label="Phone"
                        fullWidth
                        {...register("phone")}
                    />

                </Grid>

                {/* Job Title */}

                <Grid
                    size={{
                        xs: 12,
                        md: 6,
                    }}
                >

                    <TextField
                        label="Job Title"
                        fullWidth
                        {...register(
                            "jobTitle"
                        )}
                    />

                </Grid>

            </Grid>

            <Button
                sx={{ mt: 3 }}
                type="submit"
                variant="contained"
            >
                {user
                    ? "Update User"
                    : "Create User"}
            </Button>

        </form>
    );
}