import React from "react";
import { Button, FormControl, Grid, InputAdornment, InputLabel, OutlinedInput, Typography } from "@material-ui/core";
import axios from "axios";

const Investor = () => {
    const [investment, setInvestment] = React.useState("");
    const [share, setShare] = React.useState("");
    const [costPerCar, setCostPerCar] = React.useState("");

    const [roi, setRoi] = React.useState("?");
    const [amortisationMonths, setAmortisationMonths] = React.useState("?");
    const [amortisationYears, setAmortisationYears] = React.useState("?");

    const handleInvestementInput = (event) => {
        if (event.target.value === "" || /^[0-9]+\.{0,1}[0-9]{0,2}$/.test(event.target.value)) {
            setInvestment(event.target.value);
        }
    }

    const handleCostInput = (event) => {
        if (event.target.value === "" || /^[0-9]+\.{0,1}[0-9]{0,2}$/.test(event.target.value)) {
            setCostPerCar(event.target.value);
        }
    }

    const handleShareInput = (event) => {
        if (event.target.value === "" || (/^[0-9]+\.{0,1}[0-9]{0,2}$/.test(event.target.value) && parseFloat(event.target.value) <= 100)) {
            setShare(event.target.value);
        }
    }

    const getCalculation = () => {
        if (investment !== "" && share !== "" && costPerCar !== "") {
            axios.post("./api/", `roi,${investment},${share/100},${costPerCar}`).then((response) => {
                setRoi(response.data.roi);
                setAmortisationMonths(response.data.months);
                setAmortisationYears(response.data.years);
            });
        }
    };

    return (
        <Grid
            container
            direction="row"
            justify="center"
            alignItems="center"
            spacing={2}
        >
            <Grid item xs={12}>
                <Typography variant="h3">ROI-Rechner</Typography>
            </Grid>
            <Grid item sm={6}>
                <FormControl fullWidth variant="outlined">
                    <InputLabel htmlFor="investment-input">Investment</InputLabel>
                    <OutlinedInput
                        id="investment-input"
                        label="Investment"
                        endAdornment={<InputAdornment position="end">€</InputAdornment>}
                        labelWidth={70}
                        fullWidth
                        onChange={handleInvestementInput}
                        value={investment}
                    />
                </FormControl>
            </Grid>
            <Grid item sm={6}>
                <Typography align="right" variant="h6">ROI / Jahr: {roi}%</Typography>
            </Grid>
            <Grid item sm={6}>
                <FormControl fullWidth variant="outlined">
                    <InputLabel htmlFor="share-input">Gewinnbeteiligung</InputLabel>
                    <OutlinedInput
                        id="share-input"
                        label="Gewinnbeteiligung"
                        endAdornment={<InputAdornment position="end">%</InputAdornment>}
                        labelWidth={70}
                        fullWidth
                        onChange={handleShareInput}
                        value={share}
                    />
                </FormControl>
            </Grid>
            <Grid item sm={6}>
                <Typography align="right" variant="h6">Amortisation (in Monaten): {amortisationMonths}</Typography>
            </Grid>
            <Grid item sm={6}>
                <FormControl fullWidth variant="outlined">
                    <InputLabel htmlFor="cost-input">Kosten pro Fahrzeug</InputLabel>
                    <OutlinedInput
                        id="cost-input"
                        label="Kosten pro Fahrzeug"
                        endAdornment={<InputAdornment position="end">€</InputAdornment>}
                        labelWidth={70}
                        fullWidth
                        onChange={handleCostInput}
                        value={costPerCar}
                    />
                </FormControl>
            </Grid>
            <Grid item sm={6}>
                <Typography align="right" variant="h6">Amortisation (in Jahren): {amortisationYears}</Typography>
            </Grid>
            <Grid item sm={12}>
                <Button
                    color="primary"
                    size="large"
                    variant="contained"
                    onClick={getCalculation}
                >
                    Errechnen
                </Button>
            </Grid>
        </Grid>
    );
}

export default Investor;