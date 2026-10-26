import { useState } from "react";
import { Box } from "@mui/material";

import {
  Alert,
  Button,
  CircularProgress,
  Paper,
  Typography,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";

import OrganizationTable from "../../components/organization/OrganizationTable";

import OrganizationDialog from "../../components/organization/OrganizationDialog";

import useOrganizations from "../../hooks/useOrganizations";

import DeleteOrganizationDialog from "../../components/organization/DeleteOrganizationDialog";

export default function OrganizationList() {
const {
    organizations,
    loading,
    error,
    saveOrganization,
    removeOrganization,
} = useOrganizations();

  const [dialogOpen, setDialogOpen] = useState(false);

  const [selectedOrganization, setSelectedOrganization] = useState(null);

  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);

  const [organizationToDelete, setOrganizationToDelete] = useState(null);

  const handleSave = async (formData) => {

      const success = await saveOrganization(
          formData,
          selectedOrganization
      );

      if (success) {

          setDialogOpen(false);

          setSelectedOrganization(null);

      }

  };

  const handleDelete = async () => {

    const success = await removeOrganization(
        organizationToDelete.id
    );

    if (success) {

        setDeleteDialogOpen(false);

        setOrganizationToDelete(null);

    }

  };

  return (
    <Paper sx={{ p: 3 }}>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 3,
        }}
      >
<Box>
    <Typography
        variant="h4"
        fontWeight={600}
    >
        Organizations
    </Typography>

    <Typography
        variant="body2"
        color="text.secondary"
        sx={{ mt: 0.5 }}
    >
        Manage organizations registered
        with Nexus
    </Typography>
</Box>

  <Button
    variant="contained"
    startIcon={<AddIcon />}
    onClick={() => {
        setSelectedOrganization(null);
        setDialogOpen(true);
    }}>
    Add Organization
  </Button>
</Box>

<Box
    sx={{
        mb: 2,
        display: "flex",
        alignItems: "center",
        gap: 1,
    }}
>
    <Typography
        variant="body2"
        color="text.secondary"
    >
        Total Organizations:
    </Typography>

    <Typography
        variant="body2"
        fontWeight={700}
    >
        {organizations.length}
    </Typography>
</Box>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      {loading ? (
        <Box
            sx={{
                textAlign: "center",
                mt: 5,
            }}
        >
          <CircularProgress />
        </Box>
      ) : (
        <OrganizationTable
          organizations={organizations}
          loading={loading}
          onEdit={(org) => {
              setSelectedOrganization(org);
              setDialogOpen(true);
          }}
          onDelete={(org) => {

            setOrganizationToDelete(org);

            setDeleteDialogOpen(true);

          }}
        />
      )}

      <OrganizationDialog
        open={dialogOpen}
        organization={selectedOrganization}
        onClose={() => {

          setDialogOpen(false);

          setSelectedOrganization(null);

        }}
        onSubmit={handleSave}
      />

      <DeleteOrganizationDialog
        open={deleteDialogOpen}
        organization={organizationToDelete}
        onClose={() => {

            setDeleteDialogOpen(false);

            setOrganizationToDelete(null);

        }}
        onConfirm={handleDelete}
      />
    </Paper>
    
    
  );
}