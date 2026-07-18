import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
} from "@mui/material";

import OrganizationForm from "../../pages/organizations/OrganizationForm";

export default function OrganizationDialog({
  open,
  onClose,
  organization,
  onSubmit,
}) {
  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth="md"
    >
      <DialogTitle>
        {organization ? "Edit Organization" : "Add Organization"}
      </DialogTitle>

      <DialogContent dividers>
        <OrganizationForm
          organization={organization}
          onSubmit={onSubmit}
        />
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose}>
          Cancel
        </Button>
      </DialogActions>
    </Dialog>
  );
}