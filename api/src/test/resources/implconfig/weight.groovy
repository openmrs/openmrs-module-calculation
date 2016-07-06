import org.openmrs.Concept
import org.openmrs.Person
import org.openmrs.api.ObsService
import org.openmrs.calculation.EvaluationInstanceData
import org.openmrs.calculation.patient.PatientAtATimeCalculation
import org.openmrs.calculation.patient.PatientCalculationContext
import org.openmrs.calculation.result.CalculationResult
import org.openmrs.calculation.result.SimpleResult
import org.springframework.beans.factory.annotation.Autowired

class WeightCalculation extends PatientAtATimeCalculation {

    @Autowired
    private ObsService obsService;

    @Override
    CalculationResult evaluateForPatient(EvaluationInstanceData instanceData, Integer patientId,
                                         Map<String, Object> parameterValues, PatientCalculationContext context) {
        def weights = obsService.getObservationsByPersonAndConcept(new Person(patientId), new Concept(5089))
        return weights ? new SimpleResult(weights.first().getValueNumeric(), this) : null;
    }

}