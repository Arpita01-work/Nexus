import { useEffect } from "react";
import { useForm } from "react-hook-form";

import {
  Button,
  Grid,
  TextField,
} from "@mui/material";

export default function OrganizationForm({
  organization,
  onSubmit,
}) {

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm();

  useEffect(() => {
    if (organization) {
      reset(organization);
    } else {
      reset({
        name: "",
        code: "",
        email: "",
        phone: "",
        website: "",
        description: "",
      });
    }
  }, [organization, reset]);

  return (
    <form onSubmit={handleSubmit(onSubmit)}>

      <Grid container spacing={2} sx={{ mt: 1 }}>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            label="Organization Name"
            fullWidth
            {...register("name", {
              required: "Organization name is required",
            })}
            error={!!errors.name}
            helperText={errors.name?.message}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            label="Code"
            fullWidth
            {...register("code", {
              required: "Code is required",
            })}
            error={!!errors.code}
            helperText={errors.code?.message}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            label="Email"
            fullWidth
            {...register("email")}
          />
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TextField
            label="Phone"
            fullWidth
            {...register("phone")}
          />
        </Grid>

        <Grid size={{ xs: 12 }}>
          <TextField
            label="Website"
            fullWidth
            {...register("website")}
          />
        </Grid>

        <Grid size={{ xs: 12 }}>
          <TextField
            label="Description"
            fullWidth
            rows={4}
            multiline
            {...register("description")}
          />
        </Grid>

      </Grid>

      <Button
        sx={{ mt: 3 }}
        type="submit"
        variant="contained"
      >
        Save Organization
      </Button>

    </form>
  );
}