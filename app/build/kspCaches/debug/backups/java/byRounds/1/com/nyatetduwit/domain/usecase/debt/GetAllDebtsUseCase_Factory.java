package com.nyatetduwit.domain.usecase.debt;

import com.nyatetduwit.domain.repository.DebtRepository;
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
public final class GetAllDebtsUseCase_Factory implements Factory<GetAllDebtsUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public GetAllDebtsUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAllDebtsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAllDebtsUseCase_Factory create(Provider<DebtRepository> repositoryProvider) {
    return new GetAllDebtsUseCase_Factory(repositoryProvider);
  }

  public static GetAllDebtsUseCase newInstance(DebtRepository repository) {
    return new GetAllDebtsUseCase(repository);
  }
}
