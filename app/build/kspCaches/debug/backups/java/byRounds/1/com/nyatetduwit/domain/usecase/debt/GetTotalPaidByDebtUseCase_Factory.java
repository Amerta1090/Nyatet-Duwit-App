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
public final class GetTotalPaidByDebtUseCase_Factory implements Factory<GetTotalPaidByDebtUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public GetTotalPaidByDebtUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTotalPaidByDebtUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTotalPaidByDebtUseCase_Factory create(
      Provider<DebtRepository> repositoryProvider) {
    return new GetTotalPaidByDebtUseCase_Factory(repositoryProvider);
  }

  public static GetTotalPaidByDebtUseCase newInstance(DebtRepository repository) {
    return new GetTotalPaidByDebtUseCase(repository);
  }
}
