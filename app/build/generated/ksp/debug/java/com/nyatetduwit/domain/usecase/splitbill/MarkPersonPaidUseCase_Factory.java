package com.nyatetduwit.domain.usecase.splitbill;

import com.nyatetduwit.domain.repository.SplitBillRepository;
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
public final class MarkPersonPaidUseCase_Factory implements Factory<MarkPersonPaidUseCase> {
  private final Provider<SplitBillRepository> repositoryProvider;

  public MarkPersonPaidUseCase_Factory(Provider<SplitBillRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MarkPersonPaidUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static MarkPersonPaidUseCase_Factory create(
      Provider<SplitBillRepository> repositoryProvider) {
    return new MarkPersonPaidUseCase_Factory(repositoryProvider);
  }

  public static MarkPersonPaidUseCase newInstance(SplitBillRepository repository) {
    return new MarkPersonPaidUseCase(repository);
  }
}
