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
public final class MarkSettledUseCase_Factory implements Factory<MarkSettledUseCase> {
  private final Provider<SplitBillRepository> repositoryProvider;

  public MarkSettledUseCase_Factory(Provider<SplitBillRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public MarkSettledUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static MarkSettledUseCase_Factory create(
      Provider<SplitBillRepository> repositoryProvider) {
    return new MarkSettledUseCase_Factory(repositoryProvider);
  }

  public static MarkSettledUseCase newInstance(SplitBillRepository repository) {
    return new MarkSettledUseCase(repository);
  }
}
