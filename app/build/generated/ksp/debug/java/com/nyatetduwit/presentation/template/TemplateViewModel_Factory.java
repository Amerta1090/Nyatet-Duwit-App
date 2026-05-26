package com.nyatetduwit.presentation.template;

import com.nyatetduwit.domain.usecase.template.DeleteTemplateUseCase;
import com.nyatetduwit.domain.usecase.template.GetTemplatesUseCase;
import com.nyatetduwit.domain.usecase.template.ToggleTemplatePinUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class TemplateViewModel_Factory implements Factory<TemplateViewModel> {
  private final Provider<GetTemplatesUseCase> getTemplatesUseCaseProvider;

  private final Provider<DeleteTemplateUseCase> deleteTemplateUseCaseProvider;

  private final Provider<ToggleTemplatePinUseCase> togglePinUseCaseProvider;

  public TemplateViewModel_Factory(Provider<GetTemplatesUseCase> getTemplatesUseCaseProvider,
      Provider<DeleteTemplateUseCase> deleteTemplateUseCaseProvider,
      Provider<ToggleTemplatePinUseCase> togglePinUseCaseProvider) {
    this.getTemplatesUseCaseProvider = getTemplatesUseCaseProvider;
    this.deleteTemplateUseCaseProvider = deleteTemplateUseCaseProvider;
    this.togglePinUseCaseProvider = togglePinUseCaseProvider;
  }

  @Override
  public TemplateViewModel get() {
    return newInstance(getTemplatesUseCaseProvider.get(), deleteTemplateUseCaseProvider.get(), togglePinUseCaseProvider.get());
  }

  public static TemplateViewModel_Factory create(
      Provider<GetTemplatesUseCase> getTemplatesUseCaseProvider,
      Provider<DeleteTemplateUseCase> deleteTemplateUseCaseProvider,
      Provider<ToggleTemplatePinUseCase> togglePinUseCaseProvider) {
    return new TemplateViewModel_Factory(getTemplatesUseCaseProvider, deleteTemplateUseCaseProvider, togglePinUseCaseProvider);
  }

  public static TemplateViewModel newInstance(GetTemplatesUseCase getTemplatesUseCase,
      DeleteTemplateUseCase deleteTemplateUseCase, ToggleTemplatePinUseCase togglePinUseCase) {
    return new TemplateViewModel(getTemplatesUseCase, deleteTemplateUseCase, togglePinUseCase);
  }
}
