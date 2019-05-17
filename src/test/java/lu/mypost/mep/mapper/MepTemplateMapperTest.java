package lu.mypost.mep.mapper;

import lu.mypost.mep.model.enums.Status;
import lu.mypost.mep.model.document.mep.Step;
import lu.mypost.mep.model.document.mep.Stepset;
import lu.mypost.mep.model.document.template.MepTemplate;
import lu.mypost.mep.model.response.MepTemplateResponse;
import lu.mypost.mep.model.response.StepResponse;
import lu.mypost.mep.model.response.StepsetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
public class MepTemplateMapperTest {


    @Test
    public void createResponseWithFullMeplTemplate() {

        MepTemplate input = MepTemplate.builder()
                .id("1")
                .name("test")
                .stepsets(Arrays.asList(
                        Stepset.builder().id("2").name("test").order(1).steps(Arrays.asList(
                                Step.builder().id("21").name("test").order(1).status(Status.NA).build(),
                                Step.builder().id("22").name("test").order(2).status(Status.OK).build()
                        )).build(),
                        Stepset.builder().id("3").name("test").order(2).steps(Arrays.asList(
                                Step.builder().id("31").name("test").order(1).status(Status.OK).build(),
                                Step.builder().id("32").name("test").order(2).status(Status.PENDING).build()
                        )).build())
                )
                .build();

        MepTemplateResponse expectedResult = MepTemplateResponse.builder()
                .id("1")
                .name("test")
                .stepsets(Arrays.asList(
                        StepsetResponse.builder().id("2").name("test").order(1).steps(Arrays.asList(
                                StepResponse.builder().id("21").name("test").order(1).status(Status.NA).build(),
                                StepResponse.builder().id("22").name("test").order(2).status(Status.OK).build()
                        )).build(),
                        StepsetResponse.builder().id("3").name("test").order(2).steps(Arrays.asList(
                                StepResponse.builder().id("31").name("test").order(1).status(Status.OK).build(),
                                StepResponse.builder().id("32").name("test").order(2).status(Status.PENDING).build()
                        )).build()
                ))
                .build();

        MepTemplateResponse actualResult = MepTemplateMapper.createResponse(input);

        assertEquals("Mapping of mep template to response failed", expectedResult, actualResult);
    }

}
